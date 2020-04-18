package Entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "schedule_time")
    private String scheduleTime;
}
