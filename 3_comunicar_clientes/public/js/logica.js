var socket = io();
var list = document.querySelector('#not');
//Elementos de HTML
let mensaje = document.getElementById('mensaje');
let usuario = document.getElementById('usuario');
let salida = document.getElementById('salida');
let notificaciones = document.getElementById('notificaciones');
let boton = document.getElementById('enviar');

var clientes = [];

boton.addEventListener('click', function () {
  var data = {
    mensaje: mensaje.value,
    usuario: usuario.value
  }
  socket.emit('chat:mensaje', data
  );
});

mensaje.addEventListener('keypress', function () {
  socket.emit('chat:escribiendo', usuario.value);
});

socket.on('chat:mensaje', function (data) {
  salida.innerHTML += '<p><strong>' + data.usuario + '</strong>: ' + data.mensaje + '</p>';
});

socket.on('chat:escribiendo', function (data) {
  notificaciones.innerHTML = '';
  notificaciones.innerHTML = '<p><em>' + data + '</em> está escribiendo...</p>';
});

socket.on('socket_desconectado', function (data) {
  console.log(data);
  clientes = clientes.filter(function (cliente) {
    console.log(cliente);
    return cliente.id != data.id;
  });

});

socket.on('socket_conectado', function (data) {
  console.log(data);
});
