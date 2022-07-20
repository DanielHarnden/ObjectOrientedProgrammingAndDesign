google.charts.load('current', {'packages':['sankey']});
//        google.charts.setOnLoadCallback(drawChart);

var inputTextField;

async function drawChart() {

    inputTextField = document.getElementById("codefield");
    msg = document.getElementById('search_msg');
    var serviceURL = "http://localhost:8080/prerequisites?prefix=" + (inputTextField.value).toUpperCase();
    console.log(serviceURL);
    msg.innerText = "Searching..."
    let request = new XMLHttpRequest();
    var jsondata;

    request.open("GET", serviceURL); // parameterize using contents of form field
    request.send();

    request.onload = () => {
        if (request.status == 200){
            jsondata = JSON.parse(request.response);
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'From');
            data.addColumn('string', 'To');
            data.addColumn('number', 'Weight');
            data.addColumn({type:'string', role:'tooltip'});

            console.log("Reading data");
            console.log(jsondata);
            if (jsondata.adjacencyList.length > 0) {
                msg.innerText = "";
                for (let i = 0; i < jsondata.adjacencyList.length; i++) {
                    row = [jsondata.adjacencyList[i].from, jsondata.adjacencyList[i].to, parseInt(jsondata.adjacencyList[i].weight), jsondata.adjacencyList[i].title];
                    data.addRow(row);
                }
                // Sets chart options.
                var options = {
                    sankey: {
                        node: {
                            nodePadding: 40,
                            interactivity: true,
                            width: 10
                        },
                    },
                    tooltip: {isHtml: true},
                    height: jsondata.adjacencyList.length * 25
                };

                // Instantiates and draws our chart, passing in some options.
                var chart = new google.visualization.Sankey(document.getElementById('sankey_multiple'));
                chart.draw(data, options);
                google.visualization.events.addListener(chart, 'select', function(){
                    var selection = chart.getSelection();
                    console.log(selection);
                    msg.innerText = selection[0].name;
                });
            } else {
                msg.innerText = "No classes found"
            }
        } else {
            console.log("Error");
        }
    }
}



function selectionChange(){
    inputTextField = document.getElementById("codefield");
    var inputDropdown = document.getElementById("codemenu");

    inputTextField.value = (inputDropdown.options[inputDropdown.selectedIndex].value).toUpperCase();
    console.log(inputTextField.value);
}