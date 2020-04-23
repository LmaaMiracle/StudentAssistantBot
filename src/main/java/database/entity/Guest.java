package database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "guests")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Guest extends User {

}
