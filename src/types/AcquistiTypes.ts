export interface MezzoBean {
    modello: string;
    targa: string;
    marca: string;
    colore: string;
    prezzo: number;
    annoImmatricolazione: number;
    dataAcquisto: string;
  }
  
  export interface AutoBean {
    mezzoBean: MezzoBean;
  }
  
  export interface MotoBean {
    mezzoBean: MezzoBean;
  }
  
  export interface TirBean {
    mezzoBean: MezzoBean;
  }
  
  export interface AcquistiBean {
    autoBean?: AutoBean;
    motoBean?: MotoBean;
    tirBean?: TirBean;
  }
  