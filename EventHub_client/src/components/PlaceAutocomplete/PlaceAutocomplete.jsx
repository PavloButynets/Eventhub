import { useState, useEffect } from "react";
import usePlacesAutocomplete, { getGeocode } from "use-places-autocomplete";
import { AutoComplete, message } from "antd";
import styles from "./PlacesAutocomplete.module.css";

export const PlacesAutocomplete = ({
  onSelectLocation,
  initialValue,
  cancelChanges,
}) => {
  const {
    ready,
    value,
    suggestions: { status, data },
    setValue,
    clearSuggestions,
  } = usePlacesAutocomplete({
    requestOptions: {
      types: ["(cities)"], // Restrict suggestions to cities
      fields: ["address_components", "geometry", "icon", "name"],
    },
    debounce: 300,
  });

  const handleInput = (value) => {
    setValue(value);
  };

  useEffect(() => {
    handleInput(initialValue);
  }, [cancelChanges]);

  const handleSelect = (value) => {
    setValue(value);
    clearSuggestions();
    getGeocode({ address: value })
      .then((results) => {
        const selectedPlace = results[0];
        const country = selectedPlace.address_components.find((component) =>
          component.types.includes("country")
        ).long_name;
        const region = selectedPlace.address_components.find(
          (component) =>
            component.types.includes("administrative_area_level_1") ||
            component.types.includes("administrative_area_level_2")
        ).long_name;
        const city = selectedPlace.address_components.find(
          (component) =>
            component.types.includes("locality") ||
            component.types.includes("sublocality") ||
            component.types.includes("postal_town")
        ).long_name;

        onSelectLocation(`${city}, ${region}, ${country}`);
      })
      .catch((error) => {
        message.info("Select another location");
        handleInput(initialValue);
      });
  };

  const options = data.map((suggestion) => ({
    value: suggestion.description,
    label: (
      <div>
        <strong className={styles.MainSuggestion}>
          {suggestion.structured_formatting.main_text}
        </strong>
        <small className={styles.SecondarySuggestion}>
          {suggestion.structured_formatting.secondary_text}
        </small>
      </div>
    ),
  }));

  return (
    <AutoComplete
      options={options}
      onSelect={handleSelect}
      onSearch={handleInput}
      onChange={(newValue) => {
        handleInput(newValue);
        onSelectLocation(newValue);
      }}
      value={value}
      disabled={!ready}
      placeholder="Where do you live?"
      className={styles.Select}
      defaultActiveFirstOption={false}
    />
  );
};
