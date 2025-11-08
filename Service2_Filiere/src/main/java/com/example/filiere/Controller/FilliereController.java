package com.example.filiere.Controller;


import com.example.filiere.dto.RequestDTO;
import com.example.filiere.dto.ResponseDTO;
import com.example.filiere.inter.FiliereService;
import com.example.filiere.shered.GlobalResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Filieres")

@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des Étudiants et Filières",
                description = "API pour gérer les étudiants et les filières : création, consultation, mise à jour et suppression",
                version = "1.0.0"
        ),
        servers = @Server(
                url = "http://localhost:8086/",
                description = "Serveur local de développement"
        )
)
public class FilliereController {

    private FiliereService filiereService;
    FilliereController (FiliereService filiereService){
        this.filiereService = filiereService;
    }

//   ************  Créer une filière
    @Operation(
            summary = "Créer une filière",
            description = "Permet de créer une nouvelle filière en envoyant les informations nécessaires dans le corps de la requête.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Filière créée avec succès"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    @PostMapping
    public ResponseEntity<GlobalResponse<ResponseDTO>> creatfiliere(@RequestBody @Valid RequestDTO requestDTO){
        filiereService.AddFilier(requestDTO);
        return  new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(filiereService.AddFilier(requestDTO)), HttpStatus.CREATED);
    }
//  **************  Lister toutes les filières
    @Operation(
            summary = "Lister toutes les filières",
            description = "Retourne la liste complète de toutes les filières enregistrées.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des filières récupérée avec succès")
            }
    )
    @GetMapping
    public ResponseEntity<GlobalResponse<List<ResponseDTO>>>  GetALL(){
        filiereService.getAllFiliere();
        return new ResponseEntity<GlobalResponse<List<ResponseDTO>>>(new GlobalResponse<List<ResponseDTO>>(filiereService.getAllFiliere()),HttpStatus.OK );
    }

//  ************   Récupérer une filière par ID
    @Operation(
            summary = "Récupérer une filière par ID",
            description = "Retourne les informations d’une filière spécifique via son identifiant unique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière trouvée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Filière non trouvée")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<ResponseDTO>>   Getfiliere(@PathVariable Long id){
        filiereService.GetOneFilier(id);
        return  new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(filiereService.GetOneFilier(id)), HttpStatus.OK);
    }

// ******************8  Mettre à jour une filière
    @Operation(
            summary = "Mettre à jour une filière",
            description = "Permet de modifier les informations d’une filière existante en précisant son ID et les nouvelles données.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière mise à jour avec succès"),
                    @ApiResponse(responseCode = "400", description = "Données invalides"),
                    @ApiResponse(responseCode = "404", description = "Filière non trouvée")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<ResponseDTO>>   updatefiliere(@PathVariable Long id,@RequestBody @Valid RequestDTO requestDTO){
        filiereService.UpdateFiliere(id,requestDTO);
        return  new ResponseEntity<GlobalResponse<ResponseDTO>>(new GlobalResponse<ResponseDTO>(filiereService.UpdateFiliere(id,requestDTO)), HttpStatus.OK);
    }


//  **************  Supprimer une filière
    @Operation(
            summary = "Supprimer une filière",
            description = "Supprime une filière en utilisant son identifiant unique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filière supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Filière non trouvée")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<String>>   Deletefiliere(@PathVariable Long id){
        filiereService.DeleteFiliere(id);
        return  new ResponseEntity<GlobalResponse<String>>(new GlobalResponse<String>(" delete filiere is done !!! "), HttpStatus.OK);

    }

}
