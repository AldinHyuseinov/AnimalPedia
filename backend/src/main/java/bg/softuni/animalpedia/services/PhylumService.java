package bg.softuni.animalpedia.services;

import bg.softuni.animalpedia.models.entities.Phylum;
import bg.softuni.animalpedia.models.enums.PhylumType;
import bg.softuni.animalpedia.repositories.PhylumRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PhylumService {
    private final PhylumRepository phylumRepository;

    @PostConstruct
    private void initPhyla() {

        if (phylumRepository.count() == 0) {

            for (PhylumType phylumConst : PhylumType.values()) {
                Phylum phylum = new Phylum();
                phylum.setType(phylumConst);
                phylum.setDescription(descriptionFor(phylumConst));

                phylumRepository.save(phylum);
            }
        }
    }

    private String descriptionFor(PhylumType phylumType) {
        StringBuilder sb = new StringBuilder();

        switch (phylumType) {
            case SPONGE ->
                    sb.append("Marine animals more commonly known as sponges and found in every ocean on earth.");

            case ANNELID -> sb.append("More complex than Platyhelminthes, these are segmented and symmetrical worms " +
                    "containing a nervous system, respiratory system, and sense organs. Examples include the common earthworm and leeches.");

            case MOLLUSK -> sb.append("The second largest phylum by species count, and the largest marine phylum. " +
                    "Invertebrates with soft unsegmented bodies. It is estimated almost a quarter of marine life falls " +
                    "in this category. Examples include clams, mussels, and snails.");

            case CHORDATE ->
                    sb.append("Vertebrates. Animals that develop a notochord, a cartilaginous skeletal rod that " +
                            "supports the body in the embryo and can often become a spine. Most animals we are familiar with, " +
                            "including dogs, horses, birds, and humans fall into this category.");

            case FLATWORM ->
                    sb.append("Typically parasitic flatworms. Lacking in any respiratory or circulatory system, " +
                            "oxygen passes through their bodies instead in a process known as diffusion. Examples include tapeworms and flukes.");

            case ARTHROPOD ->
                    sb.append("Invertebrate animals with an exoskeleton and segmented bodies. Contains insects, " +
                            "crustaceans, and arachnids. This is the largest phylum by species count. Examples include scorpions, butterflies, and shrimp");

            case CNIDARIAN ->
                    sb.append("Mostly marine animals that include over 11,000 species. Examples include coral, jellyfish, and anemones.");
        }
        return sb.toString();
    }
}
