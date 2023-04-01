document.getElementById('verify').addEventListener('click', function (event) {
    fetch("http://localhost:8000/api/animals/verify/" + event.target.value, {method: 'PATCH'})
        .then(() => location.reload())
})