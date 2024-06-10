package pl.samba.lms.raport.api;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pl.samba.lms.raport.StudentRaportData;
import pl.samba.lms.raport.SubjectInfo;
import pl.samba.lms.raport.database.RaportRepository;
import pl.samba.lms.raport.api.RaportGenerator;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RaportService {

    private final RaportRepository raportRepository;
    private final RaportGenerator raportGenerator;

    @Autowired
    public RaportService(RaportRepository raportRepository, RaportGenerator raportGenerator) {
        this.raportRepository = raportRepository;
        this.raportGenerator = raportGenerator;
    }

    public ByteArrayOutputStream generateRaport(int idPrzedmiotu) {
        // Pobranie danych o przedmiocie i studentach z bazy danych
        SubjectInfo subjectInfo = raportRepository.getSubjectInfo(idPrzedmiotu);
        List<StudentRaportData> studentDataList = raportRepository.getStudentGradesForSubject(idPrzedmiotu);

        // Wygenerowanie raportu
        return raportGenerator.createPdf(subjectInfo.getSubjectName(), subjectInfo.getGroupCode(), studentDataList);
    }
}