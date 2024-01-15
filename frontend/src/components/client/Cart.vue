<script setup>
</script>


<template>
    <div class="mt-3">
    <h1>Cart</h1>
    <div class="mt-3">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Qtd.</th>
                    <th>Livro</th>
                    <th class="text-end">Preço Un.</th>
                    <th class="text-end">Subtotal</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>2</td>
                    <td>Titulo Livro 1</td>
                    <td class="text-end">10.11 €</td>
                    <td class="text-end">20.22 €</td>
                    <td class="text-end" style="width:5rem;">
                        <button class="btn btn-info">Mais</button>
                    </td>
                    <td class="text-end" style="width:5rem;">
                        <button class="btn btn-secondary">Menos</button>
                    </td>
                    <td class="text-end" style="width:6rem;">
                        <button class="btn btn-danger">Apagar</button>
                    </td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Titulo Livro 2</td>
                    <td class="text-end">5.03 €</td>
                    <td class="text-end">15.09 €</td>
                    <td class="text-end" style="width:5rem;">
                        <button class="btn btn-info">Mais</button>
                    </td>
                    <td class="text-end" style="width:5rem;">
                        <button class="btn btn-secondary">Menos</button>
                    </td>
                    <td class="text-end" style="width:6rem;">
                        <button class="btn btn-danger">Apagar</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="row mt-4">
        <div class="col-7">
            <h2>Total: 35.31 €</h2>
        </div>
        <div class="text-end col-5">
            <button class="btn btn-primary btn-lg">Confirmar Compra</button>
        </div>
    </div>
</div>

</template>