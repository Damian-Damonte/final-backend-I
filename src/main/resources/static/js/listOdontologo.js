const url = "http://localhost:8080/odontologos";
const header = document.querySelector("header");
const formBuscar = document.querySelector("form#buscarX");
const selectBuscar = document.querySelector("form#buscarX select");
const inputBuscar = document.querySelector("form#buscarX input");
const btnQuitalFiltro = document.querySelector("form#buscarX #btnMotrarTodos")

const tbody = document.querySelector("tbody");

const form = document.querySelector("form#modificar");
const formId = document.querySelector("input#id");
const formNombre = document.querySelector("input#nombre");
const formApellido = document.querySelector("input#apellido");
const formMatricula = document.querySelector("input#matricula");
const formSubmit = document.querySelector("form button");
const errorNombre = document.querySelector("input#nombre ~ span");
const errorApellido = document.querySelector("input#apellido ~ span");
const errorMatricula = document.querySelector("input#matricula ~ span");

const settingsJWT = {
  headers: {'Authorization': "Bearer " + JWT}
};

// RELLENAR TABLA
function renderizarOdontologos(array){
  tbody.innerHTML = "";
  if(!Array.isArray(array))
    array = [array];
  array.forEach(odontologo => {
      let fila = tbody.insertRow();
      fila.id = odontologo.id;
      fila.innerHTML =
      `<td><button id="${odontologo.id}" class="btnModificar" onclick="actualizarForm(${odontologo.id})">${odontologo.id}</button></td> <td>${odontologo.nombre}</td> <td>${odontologo.apellido}</td> <td>${odontologo.matricula}</td> <td class="tdAcciones"> <button id="${odontologo.id}" class="btnEliminar" onclick="btnEliminar(${odontologo.id})"><i class="fa-regular fa-trash-can iEliminar"></i></button></td>
      `
    })

};

function rellenarTabla (){
      const settings = {
        headers: {'Authorization': "Bearer " + JWT}
      };
  fetch(url, settingsJWT)
  .then(response => response.json())
  .then(data => {
    renderizarOdontologos(data)
  });
};
rellenarTabla();


//BUSCAR ODONTOLOGO

btnQuitalFiltro.addEventListener("click", ()=>{
  btnQuitalFiltro.style.display = "none";
  rellenarTabla();
});

formBuscar.addEventListener("submit", e=>{
  e.preventDefault();
  btnQuitalFiltro.style.display = "block";
  const buscarX = selectBuscar.value;
  const valor = inputBuscar.value;
  const settings = {
    headers: {'Authorization': "Bearer " + JWT}
  };
  fetch(`${url}/${buscarX}/${valor}`, settingsJWT )
  .then(response => response.status === 200 ? response.json() : [])
  .then(data => renderizarOdontologos(data))
});


//ELIMINAR ODONTOLOGO
function btnEliminar (id){
  const configuracion = {
    method:'DELETE',
    headers: {'Authorization': 'Bearer ' + JWT}
    };

  fetch(`${url}/${id}`, configuracion)
  .then( () => {
    form.style.display = "none";
    btnQuitalFiltro.style.display = "none";
    rellenarTabla();
    form.style.display = "none"
  });
};

//ACUTALIZAR ODONTOLOGO
function actualizarForm (id){
  form.style.display = "flex";
  form.scrollIntoView();

  fetch(`${url}/id/${id}`, settingsJWT)
    .then(respueta => respueta.json())
    .then(odontologo =>{
      formId.value = odontologo.id;
      formNombre.value = odontologo.nombre;
      formApellido.value = odontologo.apellido;
      formMatricula.value = odontologo.matricula;
    });
}

form.addEventListener("submit", e =>{
  e.preventDefault();
  if(validarInputs()){
    const cambio = {
      id: formId.value,
      nombre: formNombre.value,
      apellido: formApellido.value,
      matricula: formMatricula.value
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

  if(formNombre.value === ""){
    formNombre.classList.add("inputError");
    errorNombre.style.display = "inline";
    correcto = false;
  }
  if(formApellido.value == ""){
    formApellido.classList.add("inputError");
    errorApellido.style.display = "inline";
    correcto = false;
  }
  if(formMatricula.value == ""){
    formMatricula.classList.add("inputError");
    errorMatricula.style.display = "inline";
    correcto = false;
  }
  return correcto;
}

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
}

