package com.example.filiere.Mapper;

import com.example.filiere.Entites.Filiere;
import com.example.filiere.dto.RequestDTO;
import com.example.filiere.dto.ResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FiliereMapper {


    public Filiere  DTOToFiliere(RequestDTO  requestDTO){
        Filiere filiere = new Filiere();
        BeanUtils.copyProperties(requestDTO,filiere);
        return filiere;
    }
    public ResponseDTO FiliereToDto(Filiere filiere){
        ResponseDTO responseDTO = new ResponseDTO();
        BeanUtils.copyProperties(filiere,responseDTO);
        return responseDTO;
    }
}
