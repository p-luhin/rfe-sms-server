package by.bsu.rfe.smsservice.common.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by pluhin on 3/20/16.
 */
@Data
@Entity
@Table(name = "person")
public class PersonEntity extends CreationDetails {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
  private List<GroupEntity> groups;

  @Column(name = "temporary")
  private boolean temporary;
}
