package bll.managers;

import be.Record;
import dal.dataAccessObjects.RecordDAO;

import java.util.List;

public class RecordManager {

    private RecordDAO recordDAO;

    public RecordManager() {
        recordDAO = new RecordDAO();
    }


    public void createRecord(Record record) {
        recordDAO.create(record);
    }

    public List<Record> getAbsentDays(int studentId) {
        return recordDAO.getAbsentDays(studentId);
    }
}
