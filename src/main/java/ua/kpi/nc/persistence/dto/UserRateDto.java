package ua.kpi.nc.persistence.dto;

import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;

/**
 * Created by IO on 11.05.2016.
 */
public class UserRateDto {

    private long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private int mark;
    private String interviewType;

    public UserRateDto(User user, int mark, Role role) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
        this.mark = mark;
        this.interviewType = defineInterviewType(role);
    }

    private String defineInterviewType(Role role){
        switch (RoleEnum.valueOf(role.getRoleName())){
            case ROLE_SOFT:
                return "SOFT";
            case ROLE_TECH:
                return "TECH";
        }
        throw new IllegalArgumentException("No role defined for");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }
}


