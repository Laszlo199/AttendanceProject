package be;

public class ChangeRequest {

    private int recordId;
    private StatusType status;

    public ChangeRequest(int recordId) {
        this.recordId = recordId;
        status = StatusType.PENDING;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
