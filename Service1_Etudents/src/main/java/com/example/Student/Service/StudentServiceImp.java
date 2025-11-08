package com.example.Student.Service;

import com.example.Student.DTO.FiliereDto;
import com.example.Student.DTO.RequestDTO;
import com.example.Student.DTO.ResponseDTO;
import com.example.Student.Entities.Student;
import com.example.Student.Interface.Filiere;
import com.example.Student.Interface.StudentService;
import com.example.Student.Mapper.StudentMapper;
import com.example.Student.Repo.StudentRepo;
import com.example.Student.shared.CustomResponceExcption;
import com.example.Student.shared.GlobalResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp  implements StudentService {

    private StudentRepo studentRepo;
    private Filiere filiere;
    private StudentMapper studentMapper;

    StudentServiceImp(StudentRepo studentRepo,Filiere filiere,StudentMapper studentMapper){
        this.studentRepo = studentRepo;
        this.filiere = filiere;
        this.studentMapper = studentMapper;
    }

    @Override
    public ResponseDTO ADDStudent(RequestDTO requestDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(requestDTO,student);
        ResponseEntity<GlobalResponse<FiliereDto>> response = filiere.Getfiliere(student.getIdFilier());
        GlobalResponse<FiliereDto> dataFiliere = response.getBody();
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& "+dataFiliere.getData().getLibelle());
        if(dataFiliere.getData() != null)
            studentRepo.save(student);
//        else
//            throw CustomResponceExcption.ResourceNotFound("id makinax abb "); men b3d o n9ado
        ResponseDTO responseDTO = studentMapper.StudentToDTO(student);
        responseDTO.setFiliereDto(dataFiliere.getData());
        return responseDTO;
    }


    @Override
    public ResponseDTO GetOneStudent(Long id) {
        Student student = studentRepo.findById(id).orElseThrow( () ->  CustomResponceExcption.ResourceNotFound(" this id =  "+ id +" Not found in student "));
        ResponseEntity<GlobalResponse<FiliereDto>> response = filiere.Getfiliere(student.getIdFilier());
        GlobalResponse<FiliereDto> dataFiliere = response.getBody();
        ResponseDTO responseDTO = studentMapper.StudentToDTO(student);
        if(dataFiliere.getData() != null){
            responseDTO.setFiliereDto(dataFiliere.getData());
        }
        return responseDTO ;
    }

    @Override
    public void DeleteStudent(Long id) {
        Student student = studentRepo.findById(id).orElseThrow();
        studentRepo.delete(student);
    }

    @Override
    public ResponseDTO updateStudent(Long id, RequestDTO requestDTO) {
        Student student = studentRepo.findById(id).orElseThrow();
        student.setFristname(requestDTO.getFristname());
        student.setCNE(requestDTO.getCNE());
        student.setLastname(requestDTO.getLastname());

        ResponseEntity<GlobalResponse<FiliereDto>> response = filiere.Getfiliere(requestDTO.getIdFilier());
        GlobalResponse<FiliereDto> dataFiliere = response.getBody();
        if(dataFiliere.getData() != null)
            student.setIdFilier(requestDTO.getIdFilier());
       Student studentUpdate = studentRepo.save(student);
       ResponseDTO responseDTO = studentMapper.StudentToDTO(studentUpdate);
        responseDTO.setFiliereDto(dataFiliere.getData());
        return responseDTO;
    }

    @Override
    public List<ResponseDTO> GetALLStudent() {
        List<ResponseDTO>    responseDTOS  = new ArrayList<>();
        List<Student> students = studentRepo.findAll();
        for (Student student : students){
           ResponseDTO responseDTO  =   studentMapper.StudentToDTO(student);
            ResponseEntity<GlobalResponse<FiliereDto>> response = filiere.Getfiliere(student.getIdFilier());
            GlobalResponse<FiliereDto> dataFiliere = response.getBody();
            responseDTO.setFiliereDto(dataFiliere.getData());
            responseDTOS.add(responseDTO);

        }
        return responseDTOS;
    }
}
