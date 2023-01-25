const header = document.querySelector("header");
const formBuscar = document.querySelector("form#buscarX");
const selectBuscar = document.querySelector("form#buscarX select");
const inputBuscar = document.querySelector("form#buscarX input");
const btnQuitalFiltro = document.querySelector("form#buscarX #btnMotrarTodos")

const tbodyPaciente = document.querySelector("#table_paciente tbody");
const tbodyDomicilio = document.querySelector("#table_domicilio tbody");
const btnCambioTable = document.querySelectorAll(".btnCambioTable");

const table_paciente = document.querySelector("#table_paciente");
const table_domicilio = document.querySelector("#table_domicilio");
const btnTableDomiciolio = document.querySelector("#btnDomicilio")
const btnTableInformacion = document.querySelector("#btnInformacion")

const form = document.querySelector("form#modificar");
const formId = document.querySelector("input#id");
const formNombre = document.querySelector("input#nombre");
const formApellido = document.querySelector("input#apellido");
const formEmail = document.querySelector("input#email");
const formDni = document.querySelector("input#dni");
const formFechaIngreso = document.querySelector("input#fechaIngreso");
const formCalle = document.querySelector("input#calle");
const formNumero = document.querySelector("input#numero");
const formLocalidad = document.querySelector("input#localidad");
const formProvincia = document.querySelector("input#provincia");
const formSubmit = document.querySelector("form button");
const errorApellido = document.querySelector("input#apellido ~ span");
const errorNombre = document.querySelector("input#nombre ~ span");
const errorDni = document.querySelector("input#dni ~ span");
const errorFecha = document.querySelector("input#fechaIngreso ~ span");
const url = "http://localhost:8080/pacientes";

const settingsJWT = {
  headers: {'Authorization': "Bearer " + JWT}
};

// RELLENAR TABLA

function renderizarTablaPaciente (array){
  tbodyPaciente.innerHTML = "";
  if(!Array.isArray(array))
    array = [array];
  array.forEach(paciente => {
    let fila = tbodyPaciente.insertRow();
    fila.id = "paciente_" + paciente.id;
    fila.innerHTML =
    `<td><button id="${paciente.id}" class="btnModificar" onclick="actualizarForm(${paciente.id})">${paciente.id}</button></td> <td>${paciente.apellido}</td> <td>${paciente.nombre}</td> <td>${paciente.email}</td> <td>${paciente.dni}</td> <td>${paciente.fechaIngreso}</td><td class="tdAcciones"> <button id="${paciente.id}" class="btnEliminar" onclick="btnEliminar(${paciente.id})"><i class="fa-regular fa-trash-can iEliminar"></i></button></td>
    `
  })
  
}

function rellenarTabla_paciente (){
  fetch(url, settingsJWT)
  .then(response => response.json())
  .then(data => {
    renderizarTablaPaciente(data)
  });
};
rellenarTabla_paciente();

function renderizarTablaDomicilio (array){
  tbodyDomicilio.innerHTML = "";
  if(!Array.isArray(array))
    array = [array];
  array.forEach(paciente => {
    let fila = tbodyDomicilio.insertRow();
    fila.id = "paciente_" + paciente.id;
    fila.innerHTML =
    `<td><button id="${paciente.id}" class="btnModificar" onclick="actualizarForm(${paciente.id})">${paciente.id}</button></td> <td>${paciente.domicilio.calle}</td> <td>${paciente.domicilio.numero}</td> <td>${paciente.domicilio.localidad}</td> <td>${paciente.domicilio.provincia}</td><td class="tdAcciones"> <button id="${paciente.id}" class="btnEliminar" onclick="btnEliminar(${paciente.id})"><i class="fa-regular fa-trash-can iEliminar"></i></button></td>
    `
  })
}

function rellenarTabla_domicilio (){
  fetch(url, settingsJWT)
  .then(response => response.json())
  .then(data => {
    renderizarTablaDomicilio(data);
  });
};
rellenarTabla_domicilio();


// BUSCAR PACIENTE

btnQuitalFiltro.addEventListener("click", ()=>{
  btnQuitalFiltro.style.display = "none";
  rellenarTabla_paciente();
  rellenarTabla_domicilio();
});

formBuscar.addEventListener("submit", e=>{
  e.preventDefault();
  btnQuitalFiltro.style.display = "block";
  const buscarX = selectBuscar.value;
  const valor = inputBuscar.value;

  fetch(`${url}/${buscarX}/${valor}`, settingsJWT)
  .then(response => response.status === 200 ? response.json() : [])
  .then(data => {
    renderizarTablaPaciente(data);
    renderizarTablaDomicilio(data);
  })
});



// ELIMINAR PACIENTE
function btnEliminar (id){
  const configuracion = {
    method:'DELETE',
    headers: {'Authorization': "Bearer " + JWT}
  };

  fetch(`${url}/${id}`, configuracion)
  .then( () => {
    rellenarTabla_paciente();
    rellenarTabla_domicilio();
    btnQuitalFiltro.style.display = "none";
    form.style.display = "none"
  });
}

// ACTUALIZAR PACIENTE

function actualizarForm (id){
  form.style.display = "flex";
  form.scrollIntoView();

  fetch(`${url}/id/${id}`, settingsJWT)
    .then(respueta => respueta.json())
    .then(paciente =>{
      formId.value = paciente.id;
      formNombre.value = paciente.nombre;
      formApellido.value = paciente.apellido;
      formEmail.value = paciente.email;
      formDni.value = paciente.dni;
      formFechaIngreso.value = paciente.fechaIngreso;
      formCalle.value = paciente.domicilio.calle;
      formNumero.value = paciente.domicilio.numero;
      formLocalidad.value = paciente.domicilio.localidad;
      formProvincia.value = paciente.domicilio.provincia;
    });
}

form.addEventListener("submit", e =>{
  e.preventDefault();
  if(validarInputs()){
    const cambio = {
      id: formId.value,
      nombre: formNombre.value,
      apellido: formApellido.value,
      email: formEmail.value,
      dni: formDni.value,
      fechaIngreso: formFechaIngreso.value,
      domicilio:{
        id: formId.value,
        calle: formCalle.value,
        numero: formNumero.value,
        localidad: formLocalidad.value,
        provincia: formProvincia.value
      }
    }

    const configuraciones = {
      method:'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + JWT
      },
      body: JSON.stringify(cambio)
    }

    fetch(url, configuraciones)
    .then( respuesta => respuesta.json())
    .then( () =>{
      header.scrollIntoView();
      btnQuitalFiltro.style.display = "none";
      form.reset();
      rellenarTabla_paciente();
      rellenarTabla_domicilio();
      setTimeout(()=>form.style.display = "none", 500)
    });
  }
});

// BTN CAMBIO DE TABLE
btnCambioTable.forEach(btn=>{
  btn.addEventListener("click",()=>{
    table_paciente.classList.toggle("disabled");
    table_domicilio.classList.toggle("disabled");
    btnTableDomiciolio.classList.toggle("disabled");
    btnTableInformacion.classList.toggle("disabled");
  })
})


//VALIDACIONES 

function validarInputs (){
  limipiarErrores();
  let correcto = true;

  if(formApellido.value === ""){
    formApellido.classList.add("inputError");
    errorApellido.style.display = "inline";
    correcto = false;
  }
  if(formNombre.value == ""){
    formNombre.classList.add("inputError");
    errorNombre.style.display = "inline";
    correcto = false;
  }
  if(formDni.value == ""){
    formDni.classList.add("inputError");
    errorDni.style.display = "inline";
    correcto = false;
  }
  if(formFechaIngreso.value == ""){
    formFechaIngreso.classList.add("inputError");
    errorFecha.style.display = "inline";
    correcto = false;
  }
  return correcto;
}

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
}