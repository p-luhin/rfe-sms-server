package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Created by pluhin on 9/3/16.
 */
@Data
@MappedSuperclass
public abstract class CreationDetails extends AbstractPersistable<Integer> {

  @Column(name = "created_by", nullable = false)
  private String createdBy;

  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  @PrePersist
  public void fillDetails() {
    createdBy = SecurityUtil.getCurrentUsername();
    createdDate = new Date();
  }
}
