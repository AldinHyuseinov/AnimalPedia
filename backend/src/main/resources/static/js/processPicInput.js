function isPicture() {
    let picFile = document.getElementById("picture").files[0];
    let fileNotSelectedErrElement = document.getElementById("pictureRequiredError");

    if (typeof picFile === 'undefined') {
        fileNotSelectedErrElement.style.display = "block";

        return false;
    }
    fileNotSelectedErrElement.style.display = "none";

    let errorElement = document.getElementById("pictureFormatError");
    let fileExtension = picFile.name.substr(picFile.name.lastIndexOf('.') + 1).toLowerCase();

    if (fileExtension !== "png" && fileExtension !== "jpg") {
        errorElement.style.display = "block";

        return false;
    }
    errorElement.style.display = "none";

    return true;
}

function picToString() {

    if (isPicture()) {
        let picFile = document.getElementById("picture").files[0];
        let previewImage = document.getElementById("previewImage");
        let reader = new FileReader();

        reader.onload = function (event) {
            document.getElementById("picString").value = event.target.result;
            previewImage.src = event.target.result;
        }
        reader.readAsDataURL(picFile);
    }
}