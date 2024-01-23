package pe.gob.pj.pide.ws.bean;

import java.io.Serializable;
import java.util.Arrays;

public class PersonaReniecBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nroDNI; // DNI de la persona
	private String flgVerif; // Digito de verificacion
	private String apePat; // Apellido Paterno
	private String apeMat; // Apellido Materno
	private String apeCas; // Apellido Casada
	private String nombres; // Nombres
	private String codUbiDepDom; // Ubigeo Departamento Domicilio
	private String codUbiProDom; // Ubigeo Provincia Domicilio
	private String codUbiDisDom; // Ubigeo Distrito Domicilio
	private String codUbiLocDom; // Ubigeo Localidad Domicilio
	private String depDom; // Departamento Domicilio
	private String proDom; // Provincia Domicilio
	private String disDom; // Distrito Domicilio
	private String locDom; // Localidad Domicilio
	private String estCiv; // Estado Civil
	private String graInst; // C�digo de Grado de Instrucci�n
	private String estatura; // Estatura
	private String sexo; // Sexo
	private String docSustTipDoc; // Documento Sustentatorio - Tipo Doc.
	private String docSustNroDoc; // Documento Sustentatorio - Nro Doc.
	private String codUbiDepNac; // Ubigeo Departamento Nacimiento
	private String codUbiProNac; // Ubigeo Provincia Nacimiento
	private String codUbiDisNac; // Ubigeo Distrito Nacimiento
	private String codUbiLocNac; // Ubigeo Localidad Nacimiento
	private String depNac; // Departamento Nacimiento
	private String proNac; // Provincia Nacimiento
	private String disNac; // Distrito Nacimiento
	private String locNac; // Localidad Nacimiento
	private String fecNac; // Fecha Nacimiento
	private String docPadTipDoc; // Documento Padre - Tipo Doc
	private String docPadNumDoc; // Documento Padre - Num Doc
	private String nomPad; // Nombre del Padre
	private String docMadTipDoc; // Documento Madre - Tipo Doc
	private String docMadNumDoc; // Documento Madre - Num Doc
	private String nomMad; // Nombre de la Madre
	private String fecIns; // Fecha de Inscripci�n
	private String fecExp; // Fecha de Expedici�n
	private String fecFall; // Fecha de Fallecimiento
	private String consVot; // Constancia de Votaci�n
	private String fecCad; // Fecha de caducidad
	private String restric; // Restricciones
	private String prefDir; // Prefijo direccion
	private String direccion; // Direccion
	private String nroDir; // Numero Direccion
	private String blocOChal; // Block o chalet
	private String interior; // Interior
	private String urbanizacion; // Urbanizacion
	private String etapa; // Etapa
	private String manzana; // Manzana
	private String lote; // Lote
	private String preBlocOChal; // Prefijo Bloc o Chalet
	private String preDptoPisoInt; // Prefijo Departamento Piso Interior
	private String preUrbCondResid; // Prefijo de urb cond resid.
	private String reservado; // Reservado
	private int longitudFoto; // Longitud de la Foto en Bytes
	private int longitudFirma; // Longitud de la Firma en Bytes
	private int reservadoFotoFirma1; // ReservadoFotoFirma 1
	private String reservadoFotoFirma2; // ReservadoFotoFirma 2
	private byte[] foto; // Foto
	private byte[] firma; // Firma
	private String flgImagen; // Indica si tiene foto o no
	private String tipFichaRegistral; // Tipo de Ficha registral
	private String datos; // Indica si debe mostrar datos
	
	public PersonaReniecBean() {
		super();
	}
	
	public String getNroDNI() {
		return nroDNI;
	}
	public void setNroDNI(String nroDNI) {
		this.nroDNI = nroDNI;
	}
	public String getFlgVerif() {
		return flgVerif;
	}
	public void setFlgVerif(String flgVerif) {
		this.flgVerif = flgVerif;
	}
	public String getApePat() {
		return apePat;
	}
	public void setApePat(String apePat) {
		this.apePat = apePat;
	}
	public String getApeMat() {
		return apeMat;
	}
	public void setApeMat(String apeMat) {
		this.apeMat = apeMat;
	}
	public String getApeCas() {
		return apeCas;
	}
	public void setApeCas(String apeCas) {
		this.apeCas = apeCas;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getCodUbiDepDom() {
		return codUbiDepDom;
	}
	public void setCodUbiDepDom(String codUbiDepDom) {
		this.codUbiDepDom = codUbiDepDom;
	}
	public String getCodUbiProDom() {
		return codUbiProDom;
	}
	public void setCodUbiProDom(String codUbiProDom) {
		this.codUbiProDom = codUbiProDom;
	}
	public String getCodUbiDisDom() {
		return codUbiDisDom;
	}
	public void setCodUbiDisDom(String codUbiDisDom) {
		this.codUbiDisDom = codUbiDisDom;
	}
	public String getCodUbiLocDom() {
		return codUbiLocDom;
	}
	public void setCodUbiLocDom(String codUbiLocDom) {
		this.codUbiLocDom = codUbiLocDom;
	}
	public String getDepDom() {
		return depDom;
	}
	public void setDepDom(String depDom) {
		this.depDom = depDom;
	}
	public String getProDom() {
		return proDom;
	}
	public void setProDom(String proDom) {
		this.proDom = proDom;
	}
	public String getDisDom() {
		return disDom;
	}
	public void setDisDom(String disDom) {
		this.disDom = disDom;
	}
	public String getLocDom() {
		return locDom;
	}
	public void setLocDom(String locDom) {
		this.locDom = locDom;
	}
	public String getEstCiv() {
		return estCiv;
	}
	public void setEstCiv(String estCiv) {
		this.estCiv = estCiv;
	}
	public String getGraInst() {
		return graInst;
	}
	public void setGraInst(String graInst) {
		this.graInst = graInst;
	}
	public String getEstatura() {
		return estatura;
	}
	public void setEstatura(String estatura) {
		this.estatura = estatura;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getDocSustTipDoc() {
		return docSustTipDoc;
	}
	public void setDocSustTipDoc(String docSustTipDoc) {
		this.docSustTipDoc = docSustTipDoc;
	}
	public String getDocSustNroDoc() {
		return docSustNroDoc;
	}
	public void setDocSustNroDoc(String docSustNroDoc) {
		this.docSustNroDoc = docSustNroDoc;
	}
	public String getCodUbiDepNac() {
		return codUbiDepNac;
	}
	public void setCodUbiDepNac(String codUbiDepNac) {
		this.codUbiDepNac = codUbiDepNac;
	}
	public String getCodUbiProNac() {
		return codUbiProNac;
	}
	public void setCodUbiProNac(String codUbiProNac) {
		this.codUbiProNac = codUbiProNac;
	}
	public String getCodUbiDisNac() {
		return codUbiDisNac;
	}
	public void setCodUbiDisNac(String codUbiDisNac) {
		this.codUbiDisNac = codUbiDisNac;
	}
	public String getCodUbiLocNac() {
		return codUbiLocNac;
	}
	public void setCodUbiLocNac(String codUbiLocNac) {
		this.codUbiLocNac = codUbiLocNac;
	}
	public String getDepNac() {
		return depNac;
	}
	public void setDepNac(String depNac) {
		this.depNac = depNac;
	}
	public String getProNac() {
		return proNac;
	}
	public void setProNac(String proNac) {
		this.proNac = proNac;
	}
	public String getDisNac() {
		return disNac;
	}
	public void setDisNac(String disNac) {
		this.disNac = disNac;
	}
	public String getLocNac() {
		return locNac;
	}
	public void setLocNac(String locNac) {
		this.locNac = locNac;
	}
	public String getFecNac() {
		return fecNac;
	}
	public void setFecNac(String fecNac) {
		this.fecNac = fecNac;
	}
	public String getDocPadTipDoc() {
		return docPadTipDoc;
	}
	public void setDocPadTipDoc(String docPadTipDoc) {
		this.docPadTipDoc = docPadTipDoc;
	}
	public String getDocPadNumDoc() {
		return docPadNumDoc;
	}
	public void setDocPadNumDoc(String docPadNumDoc) {
		this.docPadNumDoc = docPadNumDoc;
	}
	public String getNomPad() {
		return nomPad;
	}
	public void setNomPad(String nomPad) {
		this.nomPad = nomPad;
	}
	public String getDocMadTipDoc() {
		return docMadTipDoc;
	}
	public void setDocMadTipDoc(String docMadTipDoc) {
		this.docMadTipDoc = docMadTipDoc;
	}
	public String getDocMadNumDoc() {
		return docMadNumDoc;
	}
	public void setDocMadNumDoc(String docMadNumDoc) {
		this.docMadNumDoc = docMadNumDoc;
	}
	public String getNomMad() {
		return nomMad;
	}
	public void setNomMad(String nomMad) {
		this.nomMad = nomMad;
	}
	public String getFecIns() {
		return fecIns;
	}
	public void setFecIns(String fecIns) {
		this.fecIns = fecIns;
	}
	public String getFecExp() {
		return fecExp;
	}
	public void setFecExp(String fecExp) {
		this.fecExp = fecExp;
	}
	public String getFecFall() {
		return fecFall;
	}
	public void setFecFall(String fecFall) {
		this.fecFall = fecFall;
	}
	public String getConsVot() {
		return consVot;
	}
	public void setConsVot(String consVot) {
		this.consVot = consVot;
	}
	public String getFecCad() {
		return fecCad;
	}
	public void setFecCad(String fecCad) {
		this.fecCad = fecCad;
	}
	public String getRestric() {
		return restric;
	}
	public void setRestric(String restric) {
		this.restric = restric;
	}
	public String getPrefDir() {
		return prefDir;
	}
	public void setPrefDir(String prefDir) {
		this.prefDir = prefDir;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getNroDir() {
		return nroDir;
	}
	public void setNroDir(String nroDir) {
		this.nroDir = nroDir;
	}
	public String getBlocOChal() {
		return blocOChal;
	}
	public void setBlocOChal(String blocOChal) {
		this.blocOChal = blocOChal;
	}
	public String getInterior() {
		return interior;
	}
	public void setInterior(String interior) {
		this.interior = interior;
	}
	public String getUrbanizacion() {
		return urbanizacion;
	}
	public void setUrbanizacion(String urbanizacion) {
		this.urbanizacion = urbanizacion;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getPreBlocOChal() {
		return preBlocOChal;
	}
	public void setPreBlocOChal(String preBlocOChal) {
		this.preBlocOChal = preBlocOChal;
	}
	public String getPreDptoPisoInt() {
		return preDptoPisoInt;
	}
	public void setPreDptoPisoInt(String preDptoPisoInt) {
		this.preDptoPisoInt = preDptoPisoInt;
	}
	public String getPreUrbCondResid() {
		return preUrbCondResid;
	}
	public void setPreUrbCondResid(String preUrbCondResid) {
		this.preUrbCondResid = preUrbCondResid;
	}
	public String getReservado() {
		return reservado;
	}
	public void setReservado(String reservado) {
		this.reservado = reservado;
	}
	public int getLongitudFoto() {
		return longitudFoto;
	}
	public void setLongitudFoto(int longitudFoto) {
		this.longitudFoto = longitudFoto;
	}
	public int getLongitudFirma() {
		return longitudFirma;
	}
	public void setLongitudFirma(int longitudFirma) {
		this.longitudFirma = longitudFirma;
	}
	public int getReservadoFotoFirma1() {
		return reservadoFotoFirma1;
	}
	public void setReservadoFotoFirma1(int reservadoFotoFirma1) {
		this.reservadoFotoFirma1 = reservadoFotoFirma1;
	}
	public String getReservadoFotoFirma2() {
		return reservadoFotoFirma2;
	}
	public void setReservadoFotoFirma2(String reservadoFotoFirma2) {
		this.reservadoFotoFirma2 = reservadoFotoFirma2;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public byte[] getFirma() {
		return firma;
	}
	public void setFirma(byte[] firma) {
		this.firma = firma;
	}
	public String getFlgImagen() {
		return flgImagen;
	}
	public void setFlgImagen(String flgImagen) {
		this.flgImagen = flgImagen;
	}
	public String getTipFichaRegistral() {
		return tipFichaRegistral;
	}
	public void setTipFichaRegistral(String tipFichaRegistral) {
		this.tipFichaRegistral = tipFichaRegistral;
	}
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "PersonaReniecBean [nroDNI=" + nroDNI + ", flgVerif=" + flgVerif + ", apePat=" + apePat + ", apeMat="
				+ apeMat + ", apeCas=" + apeCas + ", nombres=" + nombres + ", codUbiDepDom=" + codUbiDepDom
				+ ", codUbiProDom=" + codUbiProDom + ", codUbiDisDom=" + codUbiDisDom + ", codUbiLocDom=" + codUbiLocDom
				+ ", depDom=" + depDom + ", proDom=" + proDom + ", disDom=" + disDom + ", locDom=" + locDom
				+ ", estCiv=" + estCiv + ", graInst=" + graInst + ", estatura=" + estatura + ", sexo=" + sexo
				+ ", docSustTipDoc=" + docSustTipDoc + ", docSustNroDoc=" + docSustNroDoc + ", codUbiDepNac="
				+ codUbiDepNac + ", codUbiProNac=" + codUbiProNac + ", codUbiDisNac=" + codUbiDisNac + ", codUbiLocNac="
				+ codUbiLocNac + ", depNac=" + depNac + ", proNac=" + proNac + ", disNac=" + disNac + ", locNac="
				+ locNac + ", fecNac=" + fecNac + ", docPadTipDoc=" + docPadTipDoc + ", docPadNumDoc=" + docPadNumDoc
				+ ", nomPad=" + nomPad + ", docMadTipDoc=" + docMadTipDoc + ", docMadNumDoc=" + docMadNumDoc
				+ ", nomMad=" + nomMad + ", fecIns=" + fecIns + ", fecExp=" + fecExp + ", fecFall=" + fecFall
				+ ", consVot=" + consVot + ", fecCad=" + fecCad + ", restric=" + restric + ", prefDir=" + prefDir
				+ ", direccion=" + direccion + ", nroDir=" + nroDir + ", blocOChal=" + blocOChal + ", interior="
				+ interior + ", urbanizacion=" + urbanizacion + ", etapa=" + etapa + ", manzana=" + manzana + ", lote="
				+ lote + ", preBlocOChal=" + preBlocOChal + ", preDptoPisoInt=" + preDptoPisoInt + ", preUrbCondResid="
				+ preUrbCondResid + ", reservado=" + reservado + ", longitudFoto=" + longitudFoto + ", longitudFirma="
				+ longitudFirma + ", reservadoFotoFirma1=" + reservadoFotoFirma1 + ", reservadoFotoFirma2="
				+ reservadoFotoFirma2 + ", foto=" + Arrays.toString(foto) + ", firma=" + Arrays.toString(firma)
				+ ", flgImagen=" + flgImagen + ", tipFichaRegistral=" + tipFichaRegistral + ", datos=" + datos + "]";
	}
	
}