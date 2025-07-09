function animalData() {
    const specieName = document.getElementById('specieName').value.trim();
    const phylumType = document.getElementById('phylumType').value.trim();
    const animalClass = document.getElementById('animalClass').value.trim();
    const animalOrder = document.getElementById('animalOrder').value.trim();
    const animalFamily = document.getElementById('animalFamily').value.trim();
    const genus = document.getElementById('genus').value.trim();
    const scientificName = document.getElementById('scientificName').value.trim();

    const checkboxes = document.querySelectorAll('input[name="continent[]"]');
    const selectedContinents = [];
    checkboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            selectedContinents.push(checkbox.value);
        }
    });

    const conservationStatus = document.getElementById('conservationStatus').value.trim();
    const habitat = document.getElementById('habitat').value.trim();
    const dietType = document.getElementById('dietType').value.trim();
    const skinType = document.getElementById('skinType').value.trim();
    const lifespan = document.getElementById('lifespan').value.trim();
    const description = document.getElementById('description').value;

    return {
        specieName: specieName,
        phylumType: phylumType === '' ? null : phylumType,
        animalClass: animalClass === '' ? null : animalClass,
        animalOrder: animalOrder,
        animalFamily: animalFamily,
        genus: genus,
        scientificName: scientificName,
        locations: selectedContinents,
        conservationStatus: conservationStatus === '' ? null : conservationStatus,
        habitat: habitat,
        dietType: dietType === '' ? null : dietType,
        skinType: skinType === '' ? null : skinType,
        lifespan: lifespan === '' ? null : lifespan,
        description: description
    }
}