package com.example.filiere.inter;

import com.example.filiere.dto.RequestDTO;
import com.example.filiere.dto.ResponseDTO;

import java.util.List;

public interface FiliereService {

    public ResponseDTO GetOneFilier(Long id );
    public ResponseDTO AddFilier(RequestDTO requestDTO);
    public ResponseDTO UpdateFiliere(Long id , RequestDTO requestDTO);
    public void DeleteFiliere(Long id );

    public List<ResponseDTO> getAllFiliere();
}
