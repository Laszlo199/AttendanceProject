package be;

public class ChangeRequest {

    private int recordId;
    private StatusType status;

    public ChangeRequest(int recordId, StatusType status) {
        this.recordId = recordId;
        this.status = status;
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
