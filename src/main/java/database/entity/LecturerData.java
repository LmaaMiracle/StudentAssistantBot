package database.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lecturer_data")
@Data
@Getter
@Setter
public class LecturerData {
    @Id
    @Column(name = "lecturer_data_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lecturerDataId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    @Unique
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "lecturerData", cascade = CascadeType.ALL)
    private List<Lecturer> lecturerList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "lecturer_group",
            joinColumns = {@JoinColumn(name = "lecturer_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<Group> groupList;
}
