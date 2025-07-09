const deleteAnimalLinks = document.querySelectorAll('#delete-animal')

for (let deleteAnimalLink of deleteAnimalLinks) {
    deleteAnimalLink.addEventListener('click', function (event) {
        fetch(event.target.value, {method: 'DELETE'})
            .then(() => location.reload())
    })
}