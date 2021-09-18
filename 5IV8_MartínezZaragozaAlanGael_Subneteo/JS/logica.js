function XDE() {
    
	var cont = 0;
	var clas = 0;
	var ip = document.getElementById('ip').value;
	var subr = parseInt(document.getElementById('subred').value);

	if (ip == "") {
		alert("Porfavor ingresa una IP");
		return false;
	}

	if (document.getElementById('subred').value == "") {
		alert("No puede haber 0 subredes");
		return false;
	}

    for (var i = 0; i < ip.length; i++) {
		if(ip.charAt(i) == '.'){
			cont++;
		}
    }
	
	


    if(ip.length>=8 && ip.length<=15 ){
		if(cont ==3){
			var tres = parseInt(ip.substring(0,3));

			if(tres>=1 && tres<=127){
				var mascara = "255.0.0.0 /8";
				document.getElementById("clase").value="A";
				clas = 1;
			}
			else if(tres>=128 && tres<=191){
				var mascara = "255.255.0.0 /16";
				document.getElementById("clase").value = "B";
				clas = 2;
			}
			else if(tres>=192 && tres<=223){
				var mascara = "255.255.255.0 /24";
				document.getElementById("clase").value = "C";
				clas = 3;
			}
			else{

			}
		}
		else{
			document.getElementById("clase").value=null;
		}
	}
	else{
		document.getElementById("clase").value=null;
	}





	var con = 0;
	var bits = 0;
	var conC = 0;
	
	while (con < subr) {
		
		
		con = Math.pow(2,bits);
		
		bits++;
		
	}
	
	bits--;

	console.log("Exponente final: " + bits);

	var bitmask = "";
	for (let index = 0; index < bits; index++) {	
		bitmask += "1";
	}
	if (bitmask.length<8) {
		do {
			bitmask += "0";
			conC++;
		} while (bitmask.length < 8);
	}

	
	var bitdec = parseInt(bitmask, 2);
	var ar2 = [];
	var clas2 = clas;
	console.log("Bitbinario: " + bitmask + " BitDecimal: " + bitdec + " Número de ceros contados: " + conC);
	cont = 1;
	for (let index = 0; index < 4; index++) {
		
		if ((clas2 == 0) && (cont == 0)){
			ar2[index] = 0;
			
		}
		if ((clas2 == 0) && (cont == 1)){
			ar2[index] = bitdec;
			cont--;
		}
		if (clas2 != 0) {
			ar2[index] = 255;
			clas2--;
		}
		 
	}
	var masc = ar2.join(".");
	console.log("Mascara de subred adaptada: " + masc);
	document.getElementById("maskadap").value = masc;





	var ipp = ip.split(".");
	var ipp2 = masc.split(".");
	var ipp3 = "";
	var ipp4 = "";
	var ar = [];
	var ar2 = [];
	var ar3 = [];
	var ar4 = [];
	
	
	console.log("Primera parte: " + p1);
	for (let index = 0; index < ipp.length; index++) {
		
		ar[index] = parseInt(ipp[index]).toString(2);
		
		while (ar[index].length<8) {
			ar[index] = "0" + ar[index];
		}

			
	}

	for (let index = 0; index < ipp2.length; index++) {
		
		ar3[index] = parseInt(ipp2[index]).toString(2);

		while (ar3[index].length<8) {
			ar3[index] = "0" + ar3[index];
		}
		
	}
	
	var ipbin = ar.join(".");
	var ipbin2 = ar3.join(".");
	document.getElementById("ipbin").value = ipbin;
	console.log("IP en binario: " + ipbin);
	console.log("Mascara adaptada en binario: " + ipbin2);
	document.getElementById("maskbin").value = ipbin2;
	

	var rango = 256-bitdec;
	console.log("Rango: " + rango);

	
	if (clas == 1) {
		conC = conC + 16;
		conC = Math.pow(2,conC)-2;
	}
	if (clas == 2) {
		conC = conC + 8;
		conC = Math.pow(2,conC)-2;
	}
	if (clas == 3) {
		conC = Math.pow(2,conC)-2;
	}
	console.log("Número de host disponibles por subred: " + conC);
	if (conC == 0) {
		return false;
	}
	
	var tabla = document.getElementById("tabla1");
	var p1 = 0;
	var primera = "";
	var ultima = "";
	var subipi = "";
	var subipf = "";
	var pp = "";
	pp = conC;
	con = Math.pow(2,bits);
	cont = 0;
    conC = 0;
	tabla1.innerHTML = "<br><br><br><thead><td class='td'>N. de subred</td><td class='td'>IP inicial</td><td class='td'>IP inicial en binario</td><td class='td'>IP final</td><td class='td'>IP final en binario</td><td class='td'>Hosts disponibles</td></thead>";
	
	for (let index = 0; index < con; index++) {

		var tr = document.createElement("tr");
		conC++;


		if (clas == 1) {
			primera = ipp[0] + "." + cont + ".0" + ".0";
			cont = cont + rango;
			p1 = cont;
			p1--;
			ultima = ipp[0] + "." + p1 + ".255" + ".255";
			
			
			ipp3 = primera.split(".");
			ipp4 = ultima.split(".");
			for (let index = 0; (index < ipp3.length) ; index++) {
		
				ar2[index] = parseInt(ipp3[index]).toString(2);

				while (ar2[index].length<8) {
					ar2[index] = "0" + ar2[index];
				}
				
			}
			for (let index = 0; (index < ipp4.length) ; index++) {
		
				ar4[index] = parseInt(ipp4[index]).toString(2);

				while (ar4[index].length<8) {
					ar4[index] = "0" + ar4[index];
				}
				
			}
			subipi = ar2.join(".");
			subipf = ar4.join(".");
		}
		if (clas == 2) {
			primera = ipp[0] + "." + ipp[1] + "." + cont + ".0";
			cont = cont + rango;
			p1 = cont;
			p1--;
			ultima = ipp[0] + "." + ipp[1] + "." + p1 + ".255";
			ipp3 = primera.split(".");
			ipp4 = ultima.split(".");
			for (let index = 0; (index < ipp3.length) ; index++) {
		
				ar2[index] = parseInt(ipp3[index]).toString(2);

				while (ar2[index].length<8) {
					ar2[index] = "0" + ar2[index];
				}
				
			}
			for (let index = 0; (index < ipp4.length) ; index++) {
		
				ar4[index] = parseInt(ipp4[index]).toString(2);

				while (ar4[index].length<8) {
					ar4[index] = "0" + ar4[index];
				}
				
			}
			subipi = ar2.join(".");
			subipf = ar4.join(".");
			
		}
		if (clas == 3) {
			primera = ipp[0] + "." + ipp[1] + "." + ipp[2] + "." + cont;
			cont = cont + rango;
			p1 = cont;
			p1--;
			ultima = ipp[0] + "." + ipp[1] + "." + ipp[2] + "." + p1;
			
			ipp3 = primera.split(".");
			ipp4 = ultima.split(".");
			for (let index = 0; (index < ipp3.length) ; index++) {
		
				ar2[index] = parseInt(ipp3[index]).toString(2);

				while (ar2[index].length<8) {
					ar2[index] = "0" + ar2[index];
				}
				
			}
			for (let index = 0; (index < ipp4.length) ; index++) {
		
				ar4[index] = parseInt(ipp4[index]).toString(2);

				while (ar4[index].length<8) {
					ar4[index] = "0" + ar4[index];
				}
				
			}
			subipi = ar2.join(".");
			subipf = ar4.join(".");
			
		}
		tr.innerHTML = "<tr><td>"+conC+"</td><td>"+ primera +"</td><td>"+ subipi +"</td><td>"+ultima+"</td><td>"+ subipf +"</td><td>"+ pp +"</td></tr>";
		$("#tabla1").append(tr);
	}
	

}