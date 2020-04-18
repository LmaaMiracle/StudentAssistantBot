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
    private long id;

    @Column(name = "schedule_time")
    private String scheduleTime;
}
