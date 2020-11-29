package database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Member extends User {

    @Column(name = "schedule_time")
    private String scheduleTime;
}
