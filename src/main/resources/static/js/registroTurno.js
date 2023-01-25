const form = document.querySelector("form");
const inputIdOdontologo = document.querySelector("input#idOdontologo");
const inputNombreOdontologo = document.querySelector("input#nombreOdontologo");
const inputApellidoOdontologo = document.querySelector("input#apellidoOdontologo");
const inputIdPaciente = document.querySelector("input#idPaciente");
const inputNombrePaciente = document.querySelector("input#nombrePaciente");
const inputApellidoPaciente = document.querySelector("input#apellidoPaciente");
const inputFecha = document.querySelector("input#fechaTurno");
const btnRegistrar = document.querySelector('button[type="submit"]');

const dataListIdOdontologo = document.querySelector("#dataListIdOdontologo");
const dataListIdPaciente = document.querySelector("#dataListIdPaciente");

const errorIdOdontologo = document.querySelector("#idOdontologo ~ span");
const errorIdPaciente = document.querySelector("#idPaciente ~ span");
const errorFecha = document.querySelector("input#fechaTurno ~ span");

const urlTurnos = "http://localhost:8080/turnos";
const urlOdntologos = "http://localhost:8080/odontologos";
const urlPacientes = "http://localhost:8080/pacientes";

const settingsJWT = {
  headers: {'Authorization': "Bearer " + JWT}
};

let odontologosRegistrados = [];
let pacientesRegistrados = [];

// RELLENAR DATALISTS
function rellenarDataList (){
  fetch(urlOdntologos, settingsJWT)
  .then(response => response.json())
  .then(data => {
    data.forEach(odontologo =>{
      odontologosRegistrados.push(odontologo.id+"");
      dataListIdOdontologo.innerHTML += `<option value="${odontologo.id}">${odontologo.nombre} ${odontologo.apellido}</option>`
    })
  });

  fetch(urlPacientes, settingsJWT)
  .then(response => response.json())
  .then(data => {
    data.forEach(paciente =>{
      pacientesRegistrados.push(paciente.id+"");
      dataListIdPaciente.innerHTML += `<option value="${paciente.id}">${paciente.nombre} ${paciente.apellido}</option>`
    })
  });
};
rellenarDataList();


//RELLENAR OPTIONS ODONTOLOGO

inputIdOdontologo.addEventListener("input", ()=>{
  rellenarCamposOdontologo(inputIdOdontologo.value)
})

function rellenarCamposOdontologo (id){
  if(odontologosRegistrados.includes(id)){
    fetch(`${urlOdntologos}/id/${id}`, settingsJWT)
    .then(response =>response.json())
    .then(data => {
        inputNombreOdontologo.value = data.nombre;
        inputApellidoOdontologo.value = data.apellido;
    })
  }else{
    inputNombreOdontologo.value = "";
    inputApellidoOdontologo.value = "";
  }
}


//RELLENAR OPTION PACIENTE

inputIdPaciente.addEventListener("input", ()=>{
  rellenarCamposPaciente(inputIdPaciente.value)
})

function rellenarCamposPaciente (id){
  if(pacientesRegistrados.includes(id)){
    fetch(`${urlPacientes}/id/${id}`, settingsJWT)
    .then(response =>response.json())
    .then(data => {
        inputNombrePaciente.value = data.nombre;
        inputApellidoPaciente.value = data.apellido;
    })
  }else{
    inputNombrePaciente.value = "";
    inputApellidoPaciente.value = "";
  }
}


//REGISTRAR TURNO

form.addEventListener("submit", (event) => {
  event.preventDefault();
  if (validarInputs()) {
    btnRegistrar.setAttribute("disabled", "");
    
    const formData = {
      fecha:inputFecha.value,
      paciente_id:inputIdPaciente.value,
      odontologo_id:inputIdOdontologo.value
    };
    const settings = {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + JWT
      },
      body: JSON.stringify(formData)
    };

    fetch(urlTurnos, settings)
    .then(response => {
              if(response.status === 201){
                Swal.fire({
                        icon: 'success',
                        title: 'Turno registrado!',
                        toast: true,
                        position: 'top',
                        showConfirmButton: false,
                        timer: 2000,
                        timerProgressBar: true,
                      })
              };
              form.reset();
              return response.json()})
    .then(() => {
      btnRegistrar.removeAttribute("disabled");
    })
    .catch(() => {
      btnRegistrar.removeAttribute("disabled");
    })
  }
});


//VALIDACIONES

function validarInputs (){
  limipiarErrores();
  let correcto = true;
  if(inputIdOdontologo.value === ""){
    errorIdOdontologo.textContent = "Debe ingresar un ID";
    errorIdOdontologo.style.display = "inline";
    inputIdOdontologo.classList.add("inputError");
    correcto = false;
  }else if(!odontologosRegistrados.includes(inputIdOdontologo.value)){
    errorIdOdontologo.textContent = `Odontologo con ID ${inputIdOdontologo.value} no encontrado`;
    errorIdOdontologo.style.display = "inline";
    inputIdOdontologo.classList.add("inputError");
    correcto = false;
  }
  if(inputIdPaciente.value === ""){
    errorIdPaciente.textContent = "Debe ingresar un ID";
    errorIdPaciente.style.display = "inline";
    inputIdPaciente.classList.add("inputError");
    correcto = false;
  }else if(!pacientesRegistrados.includes(inputIdPaciente.value)){
    errorIdPaciente.textContent = `Paciente con ID ${inputIdPaciente.value} no encontrado`;
    errorIdPaciente.style.display = "inline";
    inputIdPaciente.classList.add("inputError");
    correcto = false;
  }
  if(inputFecha.value == ""){
    errorFecha.style.display = "inline";
    inputFecha.classList.add("inputError");
    correcto = false;
  }
  return correcto;
}

function limipiarErrores (){
  document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
  document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
}
