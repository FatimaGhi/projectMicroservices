package com.example.Student.Interface;


import com.example.Student.DTO.RequestDTO;
import com.example.Student.DTO.ResponseDTO;
import com.example.Student.Repo.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface StudentService {

    public ResponseDTO ADDStudent(RequestDTO requestDTO);
    public ResponseDTO GetOneStudent(Long id);
    public void DeleteStudent(Long id);
    public ResponseDTO updateStudent(Long id,RequestDTO requestDTO);
    public List<ResponseDTO> GetALLStudent();



}
