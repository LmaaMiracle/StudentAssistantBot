package database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Lecturer extends Member {

    @Column(name = "is_logged_in")
    private Boolean isLoggedIn;

    @ManyToOne
    @JoinColumn(name = "lecturer_data_id")
    private LecturerData lecturerData;
}
