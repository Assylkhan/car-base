/**
 * Created by Асылхан on 19.01.2015.
 */
var socket = new WebSocket("localhost:8080/do/flights");
socket.onopen = function () {
    console.log("Соединение открылось");
};
socket.onclose = function () {
    console.log ("Соединение закрылось");
};
socket.onmessage = function (event) {
    console.log ("Пришло сообщение с содержанием:", event.data);
};
