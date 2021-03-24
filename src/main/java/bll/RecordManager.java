package bll;

import be.Record;
import dal.dataAccessObjects.RecordDAO;

public class RecordManager {

    private RecordDAO recordDAO;

    public RecordManager() {
        recordDAO = new RecordDAO();
    }


    public void createRecord(Record record) {
        recordDAO.create(record);
    }
}
