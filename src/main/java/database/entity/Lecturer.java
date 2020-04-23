package database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Lecturer extends Member {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "lecturer_group",
            joinColumns = {@JoinColumn(name = "lecturer_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<Group> groupList;
}
