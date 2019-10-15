$(function(){
	calc_price();	
	$(".size").on('change',function(){
		calc_price();
	})
	$(".topping_checkbox").on('change',function(){
		calc_price();
	})
	$("#select").on('change',function(){
		calc_price();
	})
	function calc_price(){
		var size = $('input[name="size"]:checked').val();
		console.log(size);
		var toppingCount = $('input:checkbox:checked').length;
		console.log(toppingCount);
		var quantity = $('[name=quantity]').val();
		console.log(quantity);
		if (size == "M") {
			var size_price = $("#priceM").attr("value");
			console.log(size_price);
			var int_size_price = Number(size_price);
			console.log(int_size_price);
			var topping_price = toppingCount * 200;
			console.log(topping_price);
		}else {
			var size_price = $("#priceL").attr("value");
			var int_size_price = +size_price;
			var topping_price = toppingCount * 300;
		}
		var totalPrice = (int_size_price + topping_price) * quantity;
		$("#totalPrice").text(totalPrice.toLocaleString());
	}
})