package bll;

import be.*;
import be.Record;
import bll.exception.BLLexception;
import bll.util.Operations;
import bll.util.PasswordHasher;
import dal.FacadeDAL;
import dal.IFacadeDAL;
import dal.exception.DALexception;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Kuba
 * @date 3/24/2021 1:31 PM
 */
public class FacadeBLL implements IFacadeBLL{
    private static FacadeBLL facadeBLL;
    private IFacadeDAL facadeDAL = FacadeDAL.getInstance();
    private Operations operations = new Operations();

    public static IFacadeBLL getInstance(){
        if(facadeBLL==null)
            facadeBLL = new FacadeBLL();
        return facadeBLL;
    }

    private FacadeBLL() {
    }

    @Override
    public boolean establishedConnection() {
        return facadeDAL.establishedConnection();
    }

    @Override
    public String getHashedPassword(String password) {
        return PasswordHasher.getHashedPassword(password);
    }

    @Override
    public boolean verifyPassword(String email, String password, UserType userType) throws BLLexception {
        return operations.verifyPassword(email, password, userType);
    }

    @Override
    public Student getStudent(String email) throws BLLexception {
        try {
            return facadeDAL.getStudent(email);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a student");
        }
    }

    @Override
    public ScheduleEntity getCurrentLesson(int courseId) throws BLLexception {
        try {
            return facadeDAL.getCurrentLesson(courseId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a current lesson");
        }
    }

    @Override
    public Subject getSubject(int subjectId) throws BLLexception {
        try {
            return facadeDAL.getSubject(subjectId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a subject");
        }
    }

    @Override
    public Teacher getTeacher(int teacherId) throws BLLexception {
        try {
            return facadeDAL.getTeacher(teacherId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a teacher");
        }
    }

    @Override
    public Teacher getTeacher(String email) throws BLLexception {
        try {
            return facadeDAL.getTeacher(email);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a teacher");
        }
    }

    @Override
    public void createRecord(Record record) throws BLLexception {
        try {
            facadeDAL.createRecord(record);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't create a record");
        }
    }

    @Override
    public List<Record> getAbsentDays(int studentId) throws BLLexception {
        try {
            return facadeDAL.getAbsentDays(studentId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get absent days");
        }
    }

    @Override
    public void createChangeRequest(ChangeRequest newRequest) throws BLLexception {
        try {
            facadeDAL.createChangeRequest(newRequest);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't create a change request");
        }
    }

    @Override
    public List<ChangeRequest> getRequestsForTeacher(int teacherId) throws BLLexception {
        try {
            return facadeDAL.getRequestsForTeacher(teacherId);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get requests for a teacher");
        }
    }

    @Override
    public void requestAccepted(ChangeRequest changeRequest) throws BLLexception {
        try {
            facadeDAL.requestAccepted(changeRequest);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't accept the request");
        }
    }

    @Override
    public void requestDeclined(ChangeRequest changeRequest) throws BLLexception {
        try {
            facadeDAL.requestDeclined(changeRequest);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't decline the request");
        }
    }

    /**
     * we will pay a debt when refactoring
     * when moving this quotes to DB
     * @return
     */
    @Override
    public String getRandomQuote() {
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }

    List<String> quotes = Arrays.asList("“An investment in knowledge pays the best interest.”- Benjamin Franklin, writer and polymath.",
            "15 – “The capacity to learn is a gift; the ability to learn is a skill; the willingness to learn is a choice.” – " +
                    "Brian Herbert, author.",
            "12 – “I find that the harder I work, the more luck I seem to have.” – Thomas Jefferson, principal author of the Declaration of Independence.",
            "“Education is the most powerful weapon which you can use to change the world” – Nelson Mandela.",
            "“Live as if you were to die tomorrow. Learn as if you were to live forever” – Mahatma Gandhi",
            "“If You are planning for a year, sow rice; if you are planning for a decade, plant trees; if you are planning for a lifetime, educate people”" +
                    " – Chinese Proverb",
            "“Teachers open the door, but you must enter by yourself” – Chinese Proverb",
            " “The highest result of education is tolerance” – Hellen Keller",
            "“When you educate one person you can change a life, when you educate many you can change the world”- Shai Reshef"
            );
}
