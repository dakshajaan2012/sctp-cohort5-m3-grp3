package sg.edu.ntu.m3p3.entity.User;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserBulkUpdateRequest {
    private List<UUID> userIdList;
    private Boolean isActive;
    private Boolean isDeleted;
    private Boolean isAdmin;


}