package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Record koji sadrzi naziv dvorane i naziv zgrade.
 *
 * @param nazivDvorane
 * @param nazivZgrade
 */

public record Dvorana(Long id, String nazivZgrade, String nazivDvorane) implements Serializable {
}
