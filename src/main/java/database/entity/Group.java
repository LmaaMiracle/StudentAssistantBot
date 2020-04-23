package database.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "schedule_url")
    private String scheduleUrl;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> studentList;

    @ManyToMany(mappedBy = "groupList")
    private List<Lecturer> lecturerList;
}
