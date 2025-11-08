package com.example.Student.Mapper;


import com.example.Student.DTO.RequestDTO;
import com.example.Student.DTO.ResponseDTO;
import com.example.Student.Entities.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    public Student DTOToStudent(RequestDTO requestDTO){
        Student student= new Student();
        BeanUtils.copyProperties(requestDTO,student);
        return student;
    }

    public ResponseDTO StudentToDTO(Student student){
        ResponseDTO responseDTO  = new ResponseDTO();
        BeanUtils.copyProperties(student,responseDTO);
        return responseDTO;
    }


}
