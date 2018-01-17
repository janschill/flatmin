console.log('ajax.js loaded');

var income = [];

function getExpensesByUser(json) {   
  var expenses = [];
  $.getJSON('http://192.168.178.81:8080/flatmin2/app/expenses', function (data) {
    $.each(data, function (index, element) {
      if (element['idusers'] === json['idusers']) {
        expenses[index] = element;
      }
    });
    console.log(expenses);
  });

  return expenses;
}

$.getJSON('http://192.168.178.81:8080/flatmin2/app/income', function (data) {
  $.each(data, function (index, element) {
    income[index] = element;
    getExpensesByUser(income[index]);    
  });  
});

