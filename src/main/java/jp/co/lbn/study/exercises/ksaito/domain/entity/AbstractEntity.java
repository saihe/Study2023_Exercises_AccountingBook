package jp.co.lbn.study.exercises.ksaito.domain.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 基底Entity.
 */
@SuppressWarnings("unused")
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public abstract sealed class AbstractEntity permits Accounting {
  /** 作成日時. */
  protected Date createdAt;
  /** 更新日時. */
  protected Date updatedAt;

  /**
   * .
   */
  @PrePersist
  public void prePersist() {
    var now = new Date();
    createdAt = now;
    updatedAt = now;
  }

  /**
   * .
   */
  @PreUpdate
  public void preUpdate() {
    updatedAt = new Date();
  }
}
