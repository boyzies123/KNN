package part1;

public class Wine {
	private Double alcohol;
	private Double malicAcid;
	private Double ash;
	private Double alcalinityofAsh;
	private Double magnesium;
	private Double phenols;
	private Double flavanoids;
	private Double nonflavanoidPhenols;
	private Double proanthocyanins;
	private Double colorIntensity;
	private Double hue;
	private Double OD;
	private Double proline;
	private Integer classOfWine;
	public Wine(Double alcohol, Double malicAcid, Double ash, Double alcalinityofAsh, Double magnesium, Double phenols, Double flavanoids, Double nonflavanoidPhenols, Double proanthocyanins, Double colorIntensity, Double hue, Double OD, Double proline, Double classOfWine) {
		this.alcohol = alcohol;
		this.malicAcid = malicAcid;
		this.ash = ash;
		this.alcalinityofAsh = alcalinityofAsh;
		this.magnesium = magnesium;
		this.phenols = phenols;
		this.flavanoids = flavanoids;
		this.nonflavanoidPhenols = nonflavanoidPhenols;
		this.proanthocyanins = proanthocyanins;
		this.colorIntensity = colorIntensity;
		this.hue = hue;
		this.OD = OD;
		this.proline = proline;
		this.classOfWine = Integer.valueOf(((int)Math.round(classOfWine)));
	}
	public Double getAlcohol() {
		return alcohol;
	}
	public Double getMalicAcid() {
		return malicAcid;
	}
	public Double getAsh() {
		return ash;
	}
	public Double getAlcalinityOfAsh() {
		return alcalinityofAsh;
	}
	public Double getMagnesium() {
		return magnesium;
	}
	public Double getPhenols() {
		return phenols;
	}
	public Double getFlavanoids() {
		return flavanoids;
	}
	public Double getNonFlavanoidPhenols() {
		return nonflavanoidPhenols;
	}
	public Double getProanthocyanins() {
		return proanthocyanins;
	}
	public Double getColorIntensity() {
		return colorIntensity;
	}
	public Double getHue() {
		return hue;
	}
	public Double getOD() {
		return OD;
	}
	public Double getProline() {
		return proline;
	}
	public Integer getClassOfWine() {
		return classOfWine;
	}
	public void setAlcohol(Double alcohol) {
		this.alcohol = alcohol;
	}
	public void setMalicAcid(Double malicAcid) {
		this.malicAcid = malicAcid;
	}
	public void setAsh(Double ash) {
		this.ash = ash;
	}
	public void setAlcalinityOfAsh(Double alcalinityofAsh) {
		this.alcalinityofAsh = alcalinityofAsh;
	}
	public void setMagnesium(Double magnesium) {
		this.magnesium = magnesium;
	}
	public void setPhenols(Double phenols) {
		this.phenols = phenols;
	}
	public void setFlavanoids(Double flavanoids) {
		this.flavanoids = flavanoids;
	}
	public void setNonFlavanoidPhenols(Double nonflavanoidPhenols) {
		this.nonflavanoidPhenols = nonflavanoidPhenols;
	}
	public void setProanthocyanins(Double proanthocyanins) {
		this.proanthocyanins = proanthocyanins;
	}
	public void setColorIntensity(Double colorIntensity) {
		this.colorIntensity = colorIntensity;
	}
	public void setHue(Double hue) {
		this.hue = hue;
	}
	public void setOD(Double OD) {
		this.OD = OD;
	}
	public void setProline(Double proline) {
		this.proline = proline;
	}
	public void setClass(Integer classOfWine) {
		this.classOfWine = classOfWine;
	}
	
}
