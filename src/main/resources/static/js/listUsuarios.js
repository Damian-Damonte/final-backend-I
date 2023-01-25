const header = document.querySelector("header");
const formBuscar = document.querySelector("form#buscarX");
const selectBuscar = document.querySelector("form#buscarX select");
const inputBuscar = document.querySelector("form#buscarX input");
const btnQuitalFiltro = document.querySelector("form#buscarX #btnMotrarTodos")

const tbody = document.querySelector("tbody");

const form = document.querySelector("form#modificar");
const formId = document.querySelector("input#id");
const formUsername = document.querySelector("input#username");
const formRol = document.querySelector("#selectRol");
const formSubmit = document.querySelector("form button");
const errorUsername = document.querySelector("input#username ~ span");
const url = "http://localhost:8080/auth";


const settingsJWT = {
  headers: {'Authorization': "Bearer " + JWT}
};

// RELLENAR TABLA
function renderizarOdontologos(array){
  tbody.innerHTML = "";
  if(!Array.isArray(array))
    array = [array];
  array.forEach(usuario => {
      let fila = tbody.insertRow();
      fila.id = "fila"+usuario.id;
      fila.innerHTML =
      `<td><button id="${usuario.id}" class="btnModificar" onclick="actualizarForm(${usuario.id})">${usuario.id}</button ></td> <td class="usuario">${usuario.username}</td> <td>${usuario.usuarioRol}</td> <td class="tdAcciones"> <button id="${usuario.id}" class="btnEliminar" onclick="btnEliminar(${usuario.id})"><i class="fa-regular fa-trash-can iEliminar"></i></button></td>
      `
    })
    usuarioActual();
};

function rellenarTabla (){
      const settings = {
        headers: {'Authorization': "Bearer " + JWT}
      };
  fetch(`${url}/users`, settingsJWT)
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
  Swal.fire({
    title: 'Borrar tarea',
    text: "¿Está seguro que quiere borrar la tarea?",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, eliminar',
    cancelButtonText: 'Cancelar'
  })
  .then(result => {
    if(result.isConfirmed){
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
    }
  })
};

//ACUTALIZAR ODONTOLOGO
function actualizarForm (id){
  form.style.display = "flex";
  form.scrollIntoView();

  fetch(`${url}/id/${id}`, settingsJWT)
    .then(respueta => respueta.json())
    .then(usuario =>{
      formId.value = usuario.id;
      formUsername.value = usuario.username;
      formRol.value = usuario.usuarioRol;
    });
}

form.addEventListener("submit", e =>{
  e.preventDefault();
  if(validarInputs()){
    const cambio = {
      id: formId.value,
      username: formUsername.value,
      usuarioRol: formRol.value
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

  if(formUsername.value === ""){
    formUsername.classList.add("inputError");
    errorUsername.style.display = "inline";
    correcto = false;
  }
  return correcto;
}

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
}

//BORRA USUARIO ACTUAL

function usuarioActual(){
  const celdasUsuarios = document.querySelectorAll(".usuario");
  let usuarioActual = jwtParceNav.sub;
  celdasUsuarios.forEach(celda=>{
    if(celda.textContent === usuarioActual){
      celda.innerHTML += '<span class="actual"> (Actual)</span>'
    };


  })
}

function usuarioActual2(){
  const settings = {
    headers: {'Authorization': "Bearer " + JWT}
  };
  fetch(`${url}/username/${jwtParceNav.sub}`, settingsJWT )
  .then(response => response.json())
  .then(data => {
    let idUsusarioActual = data[0].id;
    const btnDelete = document.querySelector(`#fila${idUsusarioActual} .btnEliminar`);
    console.log(btnDelete);
  });
}
// usuarioActual2();

function obtenerUsernameXId(id){
  const fila = document.querySelector(`#fila${id} .usuario`);
  console.log(fila);
}

