const header = document.querySelector("header");
const formBuscar = document.querySelector("form#buscarX");
const selectBuscar = document.querySelector("form#buscarX select");
const inputBuscar = document.querySelector("form#buscarX input");
const btnQuitalFiltro = document.querySelector("form#buscarX #btnMotrarTodos")

const tbody = document.querySelector("tbody");

const form = document.querySelector("form#modificar");
const inputIdTurno = document.querySelector("input#idTurno")
const inputIdOdontologo = document.querySelector("input#idOdontologo");
const inputNombreOdontologo = document.querySelector("input#nombreOdontologo");
const inputApellidoOdontologo = document.querySelector("input#apellidoOdontologo");
const inputIdPaciente = document.querySelector("input#idPaciente");
const inputNombrePaciente = document.querySelector("input#nombrePaciente");
const inputApellidoPaciente = document.querySelector("input#apellidoPaciente");
const inputFecha = document.querySelector("input#fechaTurno");
const btnRegistrar = document.querySelector('button[type="submit"]');
const errorIdOdontologo = document.querySelector("#idOdontologo ~ span");
const errorIdPaciente = document.querySelector("#idPaciente ~ span");
const errorFecha = document.querySelector("input#fechaTurno ~ span");

const urlTurnos = "http://localhost:8080/turnos";
const urlOdntologos = "http://localhost:8080/odontologos";
const urlPacientes = "http://localhost:8080/pacientes";

const settingsJWT = {
  headers: {'Authorization': "Bearer " + JWT}
};

// RELLENAR TABLA
function renderizarOdontologos(array){
  tbody.innerHTML = "";
  if(!Array.isArray(array))
    array = [array];
    array.forEach(turno => {
      let fila = tbody.insertRow();
      fila.id = turno.id;
      fila.innerHTML =
      `<td><button id="${turno.id}" class="btnModificar" onclick="actualizarForm(${turno.id})">${turno.id}</button></td> 
      <td>${turno.fecha}</td> 
      <td>${turno.odontologo_id} - ${turno.odontologo_nombre} ${turno.odontologo_apellido}</td>
      <td>${turno.paciente_id} - ${turno.paciente_nombre} ${turno.paciente_apellido}</td>
      <td class="tdAcciones"> <button id="${turno.id}" class="btnEliminar" onclick="btnEliminar(${turno.id})"><i class="fa-regular fa-trash-can iEliminar"></i></button></td>
      `
    })
};

function rellenarTabla (){
  fetch(urlTurnos, settingsJWT)
  .then(response => response.json())
  .then(data => {
    renderizarOdontologos(data)
  });
};
rellenarTabla();


//BUSCAR TURNO

btnQuitalFiltro.addEventListener("click", ()=>{
  btnQuitalFiltro.style.display = "none";
  rellenarTabla();
});

formBuscar.addEventListener("submit", e=>{
  e.preventDefault();
  btnQuitalFiltro.style.display = "block";
  const buscarX = selectBuscar.value;
  const valor = inputBuscar.value || 0;
  fetch(`${urlTurnos}/${buscarX}/${valor}`, settingsJWT)
  .then(response => response.status === 200 ? response.json() : [])
  .then(data => renderizarOdontologos(data))
});


//ELIMINAR TURNO
function btnEliminar (id){
  const configuracion = {
    method:'DELETE',
    headers: {'Authorization': "Bearer " + JWT}
  };
  fetch(`${urlTurnos}/${id}`, configuracion)
  .then( () => {
    form.style.display = "none";
    btnQuitalFiltro.style.display = "none";
    rellenarTabla();
  });
};


//ACUTALIZAR TURNO

//COMPLETAR INPUTS CON DATA
function actualizarForm (id){
  form.style.display = "flex";
  form.scrollIntoView();

  fetch(`${urlTurnos}/id/${id}`, settingsJWT)
    .then(respueta => respueta.json())
    .then(turno =>{

      inputIdTurno.value = turno.id;
      inputFecha.value = turno.fecha;
      inputIdOdontologo.value = turno.odontologo_id;
      inputNombreOdontologo.value = turno.odontologo_nombre;
      inputApellidoOdontologo.value = turno.odontologo_apellido;
      inputIdPaciente.value = turno.paciente_id;
      inputNombrePaciente.value = turno.paciente_nombre;
      inputApellidoPaciente.value = turno.paciente_apellido;
    });
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
};


//RELLENAR OPTION PACIENTE

inputIdPaciente.addEventListener("input", ()=>{
  rellenarCamposPaciente(inputIdPaciente.value)
});

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
};


//MODIFICAR TURNO

form.addEventListener("submit", e =>{
  e.preventDefault();
  if (validarInputs()){
    const cambio = {
      id:inputIdTurno.value,
      fecha: inputFecha.value,
      paciente_id: inputIdPaciente.value,
      odontologo_id: inputIdOdontologo.value,
    }

    const configuraciones = {
      method:'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + JWT
      },
      body: JSON.stringify(cambio)
    }

    fetch(urlTurnos, configuraciones)
    .then( respuesta => respuesta.json())
    .then( () =>{
      rellenarTabla();
      header.scrollIntoView();
      form.reset();
      btnQuitalFiltro.style.display = "none";
      setTimeout(()=>form.style.display = "none", 500)
      });
  }
});


//VALIDACIONES MODIFICAR

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
