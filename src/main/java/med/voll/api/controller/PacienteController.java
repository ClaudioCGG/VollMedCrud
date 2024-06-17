package med.voll.api.controller;

import med.voll.api.model.DatosRegistroPaciente;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public void registrar(@RequestBody DatosRegistroPaciente datosRegistroPaciente) {
        pacienteRepository.save(new Paciente(datosRegistroPaciente));
    }

}