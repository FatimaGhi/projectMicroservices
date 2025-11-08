package com.example.filiere.Service;

import com.example.filiere.Entites.Filiere;
import com.example.filiere.Mapper.FiliereMapper;
import com.example.filiere.Repo.FilierRepo;
import com.example.filiere.dto.RequestDTO;
import com.example.filiere.dto.ResponseDTO;
import com.example.filiere.inter.FiliereService;
import com.example.filiere.shered.CustomResponseExecption;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FiliereServiceImp implements FiliereService {

    private FilierRepo filierRepo;
    private FiliereMapper filiereMapper;

    FiliereServiceImp(FilierRepo filierRepo,FiliereMapper filiereMapper){
        this.filiereMapper = filiereMapper;
        this.filierRepo = filierRepo;
    }
    @Override
    public ResponseDTO GetOneFilier(Long id) {
        if(!filierRepo.existsById(id))
             throw CustomResponseExecption.ResourceNotFound(" this id =  "+ id +" Not found ");

        Filiere filiere = filierRepo.findById(id).orElseThrow(() ->  CustomResponseExecption.ResourceNotFound(" this id =  "+ id +" Not found in filiere") );
        return filiereMapper.FiliereToDto(filiere);
    }

    @Override
    public ResponseDTO AddFilier(RequestDTO requestDTO) {
        Filiere filiere = new Filiere();
        BeanUtils.copyProperties(requestDTO,filiere);
        Filiere filiere1 = filierRepo.save(filiere);
        return filiereMapper.FiliereToDto(filiere1);
    }

    @Override
    public List<ResponseDTO> getAllFiliere() {
        List<Filiere>  filieres = filierRepo.findAll();
        List<ResponseDTO> responseDTOS =  new ArrayList<>();

        for ( Filiere filiere : filieres)
            responseDTOS.add(filiereMapper.FiliereToDto(filiere));

        return responseDTOS;

    }

    @Override
    public void DeleteFiliere(Long id) {
        if(!filierRepo.existsById(id))
            throw  CustomResponseExecption.ResourceNotFound(" this  id = "+ id+ " Not found ");
        filierRepo.deleteById(id);
    }

    @Override
    public ResponseDTO UpdateFiliere(Long id, RequestDTO requestDTO) {
        if(!filierRepo.existsById(id))
            throw  CustomResponseExecption.ResourceNotFound(" this id =  "+ id + " Not found ");

        Filiere filiere = filierRepo.findById(id).orElseThrow();
        filiere.setCode(requestDTO.getCode());
        filiere.setLibelle(requestDTO.getLibelle());
        filierRepo.save(filiere);
        return filiereMapper.FiliereToDto(filiere);
    }
}
