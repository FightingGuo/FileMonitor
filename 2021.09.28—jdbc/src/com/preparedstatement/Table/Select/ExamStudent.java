package com.preparedstatement.Table.Select;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class ExamStudent {
    private int FlowID;
    private int Type;
    private String IDCard;
    private String ExamCard;
    private String StudentName;
    private String Location;
    private int Grade;

    public ExamStudent(int flowID, int type, String IDCard, String examcard, String studentName, String location, int grade) {
        FlowID = flowID;
        Type = type;
        this.IDCard = IDCard;
        ExamCard = examcard;
        StudentName = studentName;
        Location = location;
        Grade = grade;
    }

    public ExamStudent() {
    }

    public int getFlowID() {
        return FlowID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public String getExamcard() {
        return ExamCard;
    }

    public void setExamcard(String examcard) {
        ExamCard = examcard;
    }

    @Override
    public String toString() {
        System.out.println("=========查询结果=========");
        return "流水号："+FlowID +"\n"+
                "四级/六级:" + Type+"\n"+
                "身份证号:" + IDCard +"\n"+
                "准考证号:"+ ExamCard+"\n"+
                "学生姓名:" + StudentName+"\n"+
                "区域:" + Location+"\n"+
                "成绩:" + Grade;

    }
}
