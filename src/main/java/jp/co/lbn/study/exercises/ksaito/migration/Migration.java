package jp.co.lbn.study.exercises.ksaito.migration;

import jakarta.persistence.Persistence;
import jakarta.persistence.TemporalType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;
import org.joda.time.DateTime;

/**
 * DBマイグレーション.
 */
@SuppressWarnings("checkstyle:LineLength")
public class Migration {
  /**
   * DBマイグレーション実施.
   */
  public void migrate() throws AppException {
    var em = Persistence
        .createEntityManagerFactory("AccountingBook", System.getProperties())
        .createEntityManager();
    var tx = em.getTransaction();
    try {
      tx.begin();
      em.createNativeQuery("create table if not exists processedMigration (fileName varchar(255) not null unique, processedAt timestamp with time zone)")
          .executeUpdate();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new AppException(e);
    }

    System.out.println("マイグレーション実行");
    List<File> files = getMigrationFiles();
    if (files.isEmpty()) {
      System.out.println("マイグレーションファイルなし");
      return;
    }

    em = Persistence
        .createEntityManagerFactory("AccountingBook", System.getProperties())
        .createEntityManager();
    tx = em.getTransaction();
    try {
      tx.begin();
      for (File migrationFile : files) {
        System.out.printf("マイグレーションファイル：%sを実行%n", migrationFile.toPath());
        try (var readTargetFile =
                 Files.exists(Path.of(migrationFile.getAbsoluteFile().toURI()))
            ? Files.newInputStream(Path.of(migrationFile.getAbsoluteFile().toURI()))
            : Thread.currentThread().getContextClassLoader().getResourceAsStream(migrationFile.toPath().toString())
        ) {
          var br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(readTargetFile)));
          if ((Long) (em.createNativeQuery("select count(*) from processedMigration where fileName = ?")
              .setParameter(1, migrationFile.getName())
              .getSingleResult()) > 0) {
            System.out.printf("%sは実施済みのためスキップ%n", migrationFile.getName());
            continue;
          }
          em.createNativeQuery(br.lines().collect(Collectors.joining())).executeUpdate();
          em.createNativeQuery("insert into processedMigration (fileName, processedAt) values (:fileName, :processedAt)")
              .setParameter("fileName", migrationFile.getName())
              .setParameter("processedAt", DateTime.now().toDate(), TemporalType.TIMESTAMP)
              .executeUpdate();
        }
      }
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new AppException(e);
    } finally {
      System.out.println("マイグレーション終了");
    }
  }

  private List<File> getMigrationFiles() throws AppException {
    List<File> files = new ArrayList<>();
    try {
      var location = getClass().getProtectionDomain().getCodeSource().getLocation();
      if (location.toString().endsWith(".jar")) {
        var zip = new ZipInputStream(location.openStream());
        for (var entry = zip.getNextEntry(); Objects.nonNull(entry); entry = zip.getNextEntry()) {
          if (entry.getName().startsWith("migrations/") && entry.getName().endsWith(".sql")) {
            files.add(new File(entry.getName()));
          }
        }
      } else {
        var dir = Path.of(Objects.requireNonNullElse(getClass().getClassLoader().getResource("migrations"), new URL("file://dummy")).toURI().getPath()).toFile();
        Stream.of(Objects.requireNonNullElse(dir.listFiles(), new File[]{}))
            .filter(f -> f.getName().endsWith(".sql"))
            .forEach(files::add);
      }
    } catch (IOException | URISyntaxException e) {
      throw new AppException(e);
    }
    return files.stream().sorted().toList();
  }
}
