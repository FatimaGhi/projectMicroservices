package com.example.Student.Controller;


import com.example.Student.DTO.RequestDTO;
import com.example.Student.DTO.ResponseDTO;
import com.example.Student.Interface.StudentService;
import com.example.Student.Service.StudentServiceImp;
import com.example.Student.shared.GlobalResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des Étudiants",
                description = "API permettant de gérer les opérations liées aux étudiants (ajout, mise à jour, suppression, consultation)",
                version = "1.0.0"
        ),
        servers = @Server(
                        url = "http://localhost:8085/",
                        description = "Serveur local de développement"
                )
)
@RestController
@RequestMapping("/api/v1/Etudiants")
public class StudentController {

    StudentServiceImp studentServiceImp;

    StudentController(StudentServiceImp studentServiceImp){
        this.studentServiceImp = studentServiceImp;
    }

    @Operation(
            summary = "Ajouter un étudiant",
            description = "Permet d’ajouter un nouvel étudiant en envoyant les informations nécessaires dans le corps de la requête (JSON).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Étudiant ajouté avec succès"),
                    @ApiResponse(responseCode = "400", description = "Les données envoyées ne sont pas valides")
            }
    )

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GlobalResponse<ResponseDTO>>  addStudent(@RequestBody @Valid RequestDTO requestDTO){
        studentServiceImp.ADDStudent(requestDTO);
        return new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(studentServiceImp.ADDStudent(requestDTO)), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Récupérer un étudiant par ID",
            description = "Retourne les informations d’un étudiant à partir de son identifiant unique (ID).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant trouvé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Aucun étudiant trouvé avec cet ID")
            }

            )

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<GlobalResponse<ResponseDTO>>   getoneStudent(@PathVariable Long id ){
        studentServiceImp.GetOneStudent(id);
        return new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(studentServiceImp.GetOneStudent(id)), HttpStatus.OK);
    }


    @Operation(
            summary = "Supprimer un étudiant",
            description = "Supprime un étudiant de la base de données à partir de son identifiant (ID).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Aucun étudiant trouvé avec cet ID")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GlobalResponse<String>>  DeleeteStudent(@PathVariable Long id ){
        studentServiceImp.DeleteStudent(id);
        return new ResponseEntity<GlobalResponse<String>>(new GlobalResponse<String>(" this student is delete  "), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Mettre à jour un étudiant",
            description = "Permet de modifier les informations d’un étudiant existant en précisant son ID et les nouvelles données.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
                    @ApiResponse(responseCode = "400", description = "Les données fournies sont invalides"),
                    @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
            }
    )
    public ResponseEntity<GlobalResponse<ResponseDTO>>   getoneStudent(@PathVariable Long id,@RequestBody RequestDTO requestDTO ){
        studentServiceImp.updateStudent(id,requestDTO);
        return new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(studentServiceImp.updateStudent(id,requestDTO)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Operation(
            summary = "Lister tous les étudiants",
            description = "Retourne la liste complète de tous les étudiants enregistrés dans la base de données.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès")
            }
    )
    public ResponseEntity<GlobalResponse<List<ResponseDTO>>>  getAllStudent(){
        studentServiceImp.GetALLStudent();
        return new ResponseEntity<GlobalResponse<List<ResponseDTO>>>(new GlobalResponse<List<ResponseDTO>>(studentServiceImp.GetALLStudent()),HttpStatus.OK);
    }


}
