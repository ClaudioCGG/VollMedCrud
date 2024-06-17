package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DatosActualizarMedicoDTO;
import med.voll.api.dto.ListadoMedicoDTO;
import med.voll.api.model.DatosDireccion;
import med.voll.api.model.DatosRegistroMedico;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    // El metodo Post debe retornar un 201 created y la url donde encontrar al medico
    @PostMapping
    public ResponseEntity<DatosActualizarMedicoDTO> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosActualizarMedicoDTO datosActualizarMedicoDTO = new  DatosActualizarMedicoDTO(medico.getId(), medico.getNombre(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosActualizarMedicoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoMedicoDTO>> listadoMedicos(@PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.DESC) Pageable paginacion) {
        //return medicoRepository.findAll(paginacion).map(ListadoMedicoDTO::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(ListadoMedicoDTO::new));
    }

    // Por buena practica debo retornar un 200 y el objeto actualizado en la ResponseEntity
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedicoDTO datosActualizarMedicoDTO){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedicoDTO.id());
        medico.actualizaDatos(datosActualizarMedicoDTO);
        return ResponseEntity.ok(new DatosActualizarMedicoDTO(medico.getId(), medico.getNombre(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),medico.getDireccion().getComplemento())));
    }

//    *** Este es un Delete Fisico y lo que quiero es un Delete Logico
//    @DeleteMapping("{id}")
//    @Transactional
//    public void borrarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    // retorno 204 en eliminar
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity borrarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosActualizarMedicoDTO(medico.getId(), medico.getNombre(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),medico.getDireccion().getComplemento())));
    }
}
