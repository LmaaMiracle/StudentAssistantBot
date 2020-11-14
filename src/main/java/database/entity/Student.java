package database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Student extends Member {

    @ManyToOne
    @JoinColumn(name = "group_name")
    private Group group;
}
