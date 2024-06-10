package pl.samba.lms.raport;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectInfo {
    private String subjectName;
    private String groupCode;

    public SubjectInfo(String subjectName, String groupCode) {
        this.subjectName = subjectName;
        this.groupCode = groupCode;
    }
}