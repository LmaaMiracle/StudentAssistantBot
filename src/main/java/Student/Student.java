package Student;

public class Student {

    private long userId;
    private String notificationTime;
    private String groupId;

    public Student(long  userId, String notificationTime, String groupId) {
        this.userId = userId;
        this.notificationTime = notificationTime;
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public String getGroupId() {
        return groupId;
    }
}
